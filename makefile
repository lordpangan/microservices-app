SHELL := /bin/bash
KIND_CLUSTER := local-dev

.PHONY: kind-up kind-down dev debug restart

kind-up:
	kind create cluster --name $(KIND_CLUSTER) --config deploy/setup-local/local-cluster.yaml

kind-down:
	kind delete cluster --name $(KIND_CLUSTER)

dev:
	devbox run -- skaffold dev

debug:
	devbox run -- skaffold debug

restart:
	kubectl rollout restart deploy -n microservices-app orders-service inventory-service payment-service
