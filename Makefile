#!/usr/bin/make -f
# https://gist.github.com/MilesChou/c278f180b2c14af44bc752cdb437ab24
IMAGE := $(shell basename $(shell pwd))
VERSION := latest

.PHONY: all build rebuild shell run

# ------------------------------------------------------------------------------

all: build

build:
	docker build -t=$(IMAGE):$(VERSION) .

rebuild:
	docker build -t=$(IMAGE):$(VERSION) --no-cache .

shell:
	docker run --rm -it $(IMAGE):$(VERSION) sh

run:
	docker run --rm -it -p 8080:8080 $(IMAGE):$(VERSION)