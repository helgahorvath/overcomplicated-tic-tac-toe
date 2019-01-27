from flask import Flask
from random import randint
from faker import Factory

app = Flask(__name__)


@app.route("/")
def get_avatar():
    fake = Factory.create()
    url = "https://api.adorable.io/avatars/face/eyes{eyes}/nose{nose}/mouth{mouth}/{color}.png".format(
        eyes=randint(1, 9), nose=randint(1, 9), mouth=randint(1, 11), color=fake.hex_color()[1:])
    return url


def main():
    app.run(host="localhost",
            port=63201,
            debug=True)


if __name__ == '__main__':
    main()