import os
from flask import Flask, jsonify, request


def create_app():
    app = Flask(__name__)
    todos = []

    @app.get("/health")
    def health():
        return jsonify(status="ok"), 200

    @app.get("/todos")
    def list_todos():
        return jsonify(todos), 200

    @app.post("/todos")
    def add_todo():
        body = request.get_json(silent=True) or {}
        title = body.get("title", "").strip()
        if not title:
            return jsonify(error="'title' is required"), 400
        todo = {"id": len(todos) + 1, "title": title, "done": False}
        todos.append(todo)
        return jsonify(todo), 201

    return app


app = create_app()

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=int(os.environ.get("APP_PORT", "8080")))
