import requests
data = {
  "username": "john_doe",
  "email": "john.doe@example.com",
  "auth_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
  "password": "strong_password123"
}

resp = requests.post('http://django-env2.eba-pmmersjp.us-west-2.elasticbeanstalk.com/userapi/',data=data)
print(resp.json())