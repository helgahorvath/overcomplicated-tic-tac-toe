from flask import Flask, jsonify
import requests


app = Flask(__name__)


@app.route("/")
def get_funfact():
    url = "https://api.chucknorris.io/jokes/random"
    try:
        data = requests.get(url)
    except requests.ConnectionError:
        return "Connection error"
    return jsonify(data.content)











def main():
    app.run(host="localhost",
            port=63200,
            debug=True)


if __name__ == '__main__':
    main()