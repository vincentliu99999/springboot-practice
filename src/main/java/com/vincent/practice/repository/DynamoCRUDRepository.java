package com.vincent.practice.repository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincent.practice.model.dao.PagedResult;
import com.vincent.practice.repository.ddbmapper.DDBMapper;
import com.vincent.practice.repository.ddbmapper.DDBModelException;
import com.vincent.practice.repository.ddbmapper.DDBTableMeta;
import com.vincent.practice.repository.ddbmapper.NOKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.BatchGetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.KeysAndAttributes;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest.Builder;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

public abstract class DynamoCRUDRepository<T> {

	private static final Encoder encoder = Base64.getUrlEncoder();

	private static final Decoder decoder = Base64.getUrlDecoder();

	@Autowired
	private DynamoDbClient ddb;

	public DynamoDbClient getDynamoDbClient() {
		return ddb;
	}

	public T getItem(T t) throws IllegalArgumentException, IllegalAccessException, DDBModelException, NOKeyException,
			InstantiationException, ClassNotFoundException, ParseException {
		DDBTableMeta meta = DDBMapper.extractEntityMeta(t, DDBMapper.GET_MODE);

		HashMap<String, AttributeValue> attributeMap = new HashMap<String, AttributeValue>();
		attributeMap.put(meta.getHashKeyName(), meta.getHashKeyAttributeValue());
		if (meta.getRangeKeyName() != null)
			attributeMap.put(meta.getRangeKeyName(), meta.getRangeKeyAttributeValue());

		GetItemRequest request = GetItemRequest.builder().key(attributeMap).tableName(meta.getTableName()).build();
		Map<String, AttributeValue> returnMap = ddb.getItem(request).item();
		if (returnMap != null && !returnMap.keySet().isEmpty()) {
			T newT = (T) t.getClass().newInstance();
			DDBMapper.populateEntity(newT, returnMap);
			return newT;
		}
		return null;
	}

	public List<T> queryByPartitionKey(T t) throws IllegalArgumentException, IllegalAccessException,
			ClassNotFoundException, InstantiationException, DDBModelException, ParseException, NOKeyException {
		DDBTableMeta meta = DDBMapper.extractEntityMeta(t, DDBMapper.GET_MODE);

		HashMap<String, AttributeValue> attrValue = new HashMap<String, AttributeValue>();
		attrValue.put(":pk", meta.getHashKeyAttributeValue());

		QueryRequest queryReq = QueryRequest.builder().tableName(meta.getTableName())
				.keyConditionExpression(meta.getHashKeyName() + " = :pk").expressionAttributeValues(attrValue).build();

		List<Map<String, AttributeValue>> returnMap = ddb.query(queryReq).items();
		List<T> retNewListT = new ArrayList<T>();
		for (Map<String, AttributeValue> map : returnMap) {
			T newT = (T) t.getClass().newInstance();
			DDBMapper.populateEntity(newT, map);
			retNewListT.add(newT);
		}
		return retNewListT;
	}

	public List<T> queryByRangeKey(T t) throws IllegalArgumentException, IllegalAccessException, DDBModelException,
			NOKeyException, InstantiationException, ClassNotFoundException, ParseException {

		DDBTableMeta meta = DDBMapper.extractEntityMeta(t, DDBMapper.GET_MODE);

		HashMap<String, AttributeValue> attrValue = new HashMap<String, AttributeValue>();
		attrValue.put(":pk", meta.getHashKeyAttributeValue());
		attrValue.put(":typeRange", meta.getRangeKeyAttributeValue());

		QueryRequest queryReq = QueryRequest.builder().tableName(meta.getTableName())
				.keyConditionExpression(
						meta.getHashKeyName() + " = :pk and begins_with(" + meta.getRangeKeyName() + ", :typeRange)")
				.expressionAttributeValues(attrValue).build();

		List<Map<String, AttributeValue>> returnMap = ddb.query(queryReq).items();
		List<T> retNewListT = new ArrayList<T>();
		for (Map<String, AttributeValue> map : returnMap) {
			T newT = (T) t.getClass().newInstance();
			DDBMapper.populateEntity(newT, map);
			retNewListT.add(newT);
		}
		return retNewListT;
	}

	public T saveItem(T t) throws IllegalArgumentException, IllegalAccessException, DDBModelException, NOKeyException {
		if (DDBMapper.autoCheckUpdateMode(t, DDBMapper.PUT_MODE)) {
			return this.updateItem(t);
		}

		DDBTableMeta meta = DDBMapper.extractEntityMeta(t, DDBMapper.PUT_MODE);
		HashMap<String, AttributeValue> attributeMap = meta.getAttributeMap();

		PutItemRequest request = PutItemRequest.builder().tableName(meta.getTableName()).item(attributeMap).build();
		ddb.putItem(request);
		return t;
	}

	/**
	 * 
	 * @param t
	 * @param updateFields 可指定更新欄位
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws DDBModelException
	 * @throws NOKeyException
	 */
	public T updateItem(T t, String... updateFields) // update
			throws IllegalArgumentException, IllegalAccessException, DDBModelException, NOKeyException {
		DDBTableMeta meta = DDBMapper.extractEntityMeta(t, DDBMapper.UPDATE_MODE, updateFields);

		UpdateItemRequest request = UpdateItemRequest.builder().tableName(meta.getTableName())
				.key(meta.getAttributeMap()).attributeUpdates(meta.getUpdatedAttributeMap()).build();

		ddb.updateItem(request);
		return t;
	}

	public <X extends Object> X counterAdd(X t, String... updateFields)
			throws IllegalArgumentException, IllegalAccessException, DDBModelException, NOKeyException,
			ClassNotFoundException, InstantiationException, ParseException {
		DDBTableMeta meta = DDBMapper.extractEntityMeta(t, DDBMapper.COUNT_MODE, updateFields);

		UpdateItemRequest request = UpdateItemRequest.builder().tableName(meta.getTableName())
				.key(meta.getAttributeMap()).attributeUpdates(meta.getUpdatedAttributeMap())
				.returnValues(ReturnValue.ALL_NEW).build();

		Map<String, AttributeValue> responseMap = ddb.updateItem(request).attributes();
		DDBMapper.populateEntity(t, responseMap);
		return t;
	}

	public int deleteItem(T t)
			throws IllegalArgumentException, IllegalAccessException, DDBModelException, NOKeyException {
		DDBTableMeta meta = DDBMapper.extractEntityMeta(t, DDBMapper.UPDATE_MODE);

		DeleteItemRequest deleteReq = DeleteItemRequest.builder().tableName(meta.getTableName())
				.key(meta.getAttributeMap()).build();

		ddb.deleteItem(deleteReq);
		return 1;
	}

	protected List<Map<String, AttributeValue>> batchGetPer100Item(String tableName,
			List<Map<String, AttributeValue>> keyItem) {

		List<Map<String, AttributeValue>> totalResponseMap = new ArrayList<Map<String, AttributeValue>>();
		List<Map<String, AttributeValue>> box = new ArrayList<Map<String, AttributeValue>>();
		int counter = 0;
		for (Map<String, AttributeValue> one : keyItem) {
			box.add(one);
			counter++;
			if (counter == 100) {
				Map<String, KeysAndAttributes> requestItems = new HashMap<>();
				requestItems.put(tableName, KeysAndAttributes.builder().keys(keyItem).build());
				BatchGetItemRequest request = BatchGetItemRequest.builder().requestItems(requestItems).build();
				List<Map<String, AttributeValue>> responseMap = ddb.batchGetItem(request).responses().get(tableName);
				totalResponseMap.addAll(responseMap);
				box.clear();
				counter = 0;
			}
		}

		if (!box.isEmpty()) {
			Map<String, KeysAndAttributes> requestItems = new HashMap<>();
			requestItems.put(tableName, KeysAndAttributes.builder().keys(keyItem).build());
			BatchGetItemRequest request = BatchGetItemRequest.builder().requestItems(requestItems).build();
			List<Map<String, AttributeValue>> responseMap = ddb.batchGetItem(request).responses().get(tableName);
			totalResponseMap.addAll(responseMap);
		}

		return totalResponseMap;
	}

	protected void batchWritePer25Item(String tableName, List<WriteRequest> keyItem) {
		int counter = 0;
		List<WriteRequest> box = new ArrayList<WriteRequest>();
		for (WriteRequest one : keyItem) {
			box.add(one);
			counter++;
			if (counter == 25) {
				HashMap<String, List<WriteRequest>> map = new HashMap<String, List<WriteRequest>>();
				map.put(tableName, box);
				BatchWriteItemRequest batchWriteItemRequest = BatchWriteItemRequest.builder().requestItems(map).build();
				ddb.batchWriteItem(batchWriteItemRequest);
				box.clear();
				counter = 0;
			}
		}
		if (!box.isEmpty()) {
			HashMap<String, List<WriteRequest>> map = new HashMap<String, List<WriteRequest>>();
			map.put(tableName, box);
			BatchWriteItemRequest batchWriteItemRequest = BatchWriteItemRequest.builder().requestItems(map).build();
			ddb.batchWriteItem(batchWriteItemRequest);
		}
	}

	protected String encLastEvaluatedKey(Map<String, AttributeValue> lastEvaluatedKey)
			throws JsonProcessingException, UnsupportedEncodingException {
		Map<String, Object> newMap = new HashMap<String, Object>();
		for (String key : lastEvaluatedKey.keySet()) {
			AttributeValue v = lastEvaluatedKey.get(key);
			if (v.n() != null) {
				newMap.put(key, new BigDecimal(v.n()));
			} else if (v.s() != null) {
				newMap.put(key, v.s());
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(newMap);

		return encoder.encodeToString(json.getBytes("UTF-8"));
	}

	protected Map<String, AttributeValue> decLastEvaluatedKey(String encString)
			throws JsonParseException, JsonMappingException, IOException {
		String json = new String(decoder.decode(encString), "UTF-8");
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = objectMapper.readValue(json, HashMap.class);
		Map<String, AttributeValue> ret = new HashMap<String, AttributeValue>();
		for (String key : jsonMap.keySet()) {
			Object v = jsonMap.get(key);
			if (v instanceof Number) {
				ret.put(key, AttributeValue.builder().n(v.toString()).build());
			} else {
				ret.put(key, AttributeValue.builder().s(v.toString()).build());
			}
		}
		return ret;
	}

	protected PagedResult<T> pagingProcess(Builder builder, Integer pageSize, String cursor) throws JsonParseException,
			JsonMappingException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			ClassNotFoundException, DDBModelException, ParseException {
		Class genericClass = ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]);
		return this.pagingProcess(builder, pageSize, cursor, (T) genericClass.newInstance());
	}

	/**
	 * DDB 分頁程序
	 * 
	 * @param builder
	 * @param pageSize
	 * @param cursor
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException
	 * @throws DDBModelException
	 * @throws ParseException
	 */
	protected PagedResult<T> pagingProcess(Builder builder, Integer pageSize, String cursor, T t)
			throws JsonParseException, JsonMappingException, IOException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, ClassNotFoundException, DDBModelException,
			ParseException {
		PagedResult<T> pager = new PagedResult<T>();

		builder.limit(pageSize);
		if (cursor != null && !cursor.equals("")) {
			pager.setPreCursor(cursor);
			builder.exclusiveStartKey(this.decLastEvaluatedKey(cursor));
		}
		QueryRequest request = builder.build();

		QueryResponse resp = ddb.query(request);
		List<Map<String, AttributeValue>> returnMap = resp.items();
		List<T> retNewListT = new ArrayList<T>();
//		Class genericClass = ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass())
//				.getActualTypeArguments()[0]);
		for (Map<String, AttributeValue> map : returnMap) {
			T newT = (T) t.getClass().newInstance();
			DDBMapper.populateEntity(newT, map);
			retNewListT.add(newT);
		}

		Map<String, AttributeValue> lastEvaluatedKey = resp.lastEvaluatedKey();
		if (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty()) {
			while (retNewListT.size() < pageSize && lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty()) {
				builder.limit(pageSize - retNewListT.size());
				builder.exclusiveStartKey(lastEvaluatedKey);
				request = builder.build();
				QueryResponse respRe = ddb.query(request);
				for (Map<String, AttributeValue> map : respRe.items()) {
					T newT = (T) t.getClass().newInstance();
					DDBMapper.populateEntity(newT, map);
					retNewListT.add(newT);
				}
				lastEvaluatedKey = respRe.lastEvaluatedKey();
			}
			if (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty())
				pager.setCursor(this.encLastEvaluatedKey(lastEvaluatedKey));
		}

		pager.setData(retNewListT);
		return pager;

	}
}