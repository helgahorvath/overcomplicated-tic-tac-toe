from flask import Flask, jsonify
from random import randint
import requests

app = Flask(__name__)


@app.route("/")
def get_comic():
    url = "https://xkcd.com/{num}/info.0.json".format(num=randint(1, 1929))
    result = requests.get(url)
    return jsonify(result.content.decode())


def main():
    app.run(host="localhost",
            port=63202,
            )


if __name__ == '__main__':
    main()