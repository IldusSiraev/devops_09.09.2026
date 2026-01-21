from flask import Flask, jsonify
import mysql.connector
import time
import os, socket

app = Flask(__name__)

def get_db_connection():
    for _ in range(10):
        try:
            return mysql.connector.connect(
                host="db",
                user="root",
                password="password",
                database="testdb"
            )
        except Exception as e:
            print("MySQL is not ready yet...", e)
            time.sleep(2)
    raise Exception("MySQL connection failed")

@app.route("/")
def home():
    return "Backend is working"

@app.get("/whoami")
def whoami():
    return jsonify({
        "hostname": socket.gethostname(),
        "pod": os.getenv("POD_NAME"),
        "node": os.getenv("NODE_NAME"),
        "podIP": os.getenv("POD_IP"),
    })

@app.route("/users")
def users():
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    cursor.execute("SELECT * FROM users")
    data = cursor.fetchall()
    cursor.close()
    conn.close()
    return jsonify(data)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)

