from app import create_app


def client():
    app = create_app()
    app.config.update(TESTING=True)
    return app.test_client()


def test_health_is_ok():
    response = client().get("/health")
    assert response.status_code == 200
    assert response.get_json() == {"status": "ok"}


def test_create_and_list_todo():
    test_client = client()
    created = test_client.post("/todos", json={"title": "Finish DevOps assignment"})
    assert created.status_code == 201
    listed = test_client.get("/todos")
    assert listed.status_code == 200
    assert listed.get_json()[0]["title"] == "Finish DevOps assignment"


def test_missing_title_is_rejected():
    response = client().post("/todos", json={})
    assert response.status_code == 400
