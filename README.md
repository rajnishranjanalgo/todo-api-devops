# Todo API DevOps Pipeline

This Flask REST API is one continuous DevOps project: Git and GitHub, Jenkins CI,
Ansible configuration, Docker packaging, and Kubernetes deployment.

## API

- `GET /health` returns the service status.
- `GET /todos` lists the in-memory tasks.
- `POST /todos` accepts `{"title":"Finish assignment"}`.

## Local test

```sh
python -m pip install -r requirements-dev.txt
python -m pytest -q
python app.py
curl http://localhost:8080/health
```

## Jenkins

Create a Pipeline job, point it at this GitHub repository, and use the included
`Jenkinsfile`. Configure GitHub webhook or SCM polling to trigger it on every push.
It tests the app, creates an archived `.tar.gz` artifact, and builds a Docker image
tagged using the commit SHA or Jenkins build number.

## Docker

```sh
docker build -t todo-api:latest .
docker run --rm -p 8080:8080 todo-api:latest
curl http://localhost:8080/health
```

## Ansible

Run from Linux/WSL with Ansible installed:

```sh
ansible-playbook -i ansible/inventory.ini ansible/playbook.yml --ask-become-pass
```

The role uses a port variable and restarts the service only when its source or
systemd definition changes.

## Kubernetes

For Minikube, load the local image before deployment:

```sh
minikube image load todo-api:latest
kubectl apply -f k8s/
kubectl get pods
kubectl scale deployment/todo-api --replicas=5
kubectl get pods
kubectl scale deployment/todo-api --replicas=3
minikube service todo-api --url
```

The deployment begins with three replicas and uses a ConfigMap plus readiness and
liveness probes. The multi-stage Dockerfile, ConfigMap, and probes satisfy the
deployment-enhancement requirement.

## Evidence to capture

1. Git branch graph with the feature branch merge.
2. Jenkins SCM/trigger/build configuration, successful console output, and artifact.
3. GitHub Actions test run.
4. Successful Ansible playbook output.
5. Docker image build and `/health` response.
6. Kubernetes pods at 3 replicas, at 5 replicas, and the API response through Service.
