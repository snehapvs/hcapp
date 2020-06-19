# Tech Challenge

### Prerequisites
* java-8 and set JAVA_HOME
* docker(optional)

### Running the application locally

Running with Docker:

```sh
$ docker-compose up
```

Running without Docker:

```sh
$ ./mvnw spring-boot:run
```

Running tests From Terminal:

```sh
$ ./mvnw test
```

## Problems discovered and Resolved
* In both cases of listing vehicals dealerId is taken through URL parameter.(This was missing in requirements doc)
* header in csv, power-in-ps is considered as kw to make reuests uniform across apis
* dealer_id and code is considered unique together, as it would resolve the issue of duplicate code when same dealer needs to update the existing listing.

## Technologies
* used spring-boot for REST framework.
* h2 for database, as it is an embedded database ideal for test applications.
* git for version control.

## API design
The app exposes three apis
* POST api For uploading csv `api/heycar/upload_csv/{dealerId}`
* POST api for taking input JSON `api/heycar/vehicle_listings/{dealerId}`
* GET api for search. `api/heycar/search` this api takes multiple params like `?make=xxx&make=yyy&make=zzz`
## Example Tests and Results
1. `curl -X GET \
  'http://localhost:8080/api/heycar/search?make=audi&make=sdd' `

* Output:
[
    {
        "make": "sdd",
        "model": "megane",
        "kw": 132,
        "year": null,
        "color": "11",
        "price": 13992,
        "code": "abc",
        "dealerId": "1"
    },
    {
        "make": "audi",
        "model": "a3",
        "kw": 111,
        "year": 2016,
        "color": "white",
        "price": 17210,
        "code": "2",
        "dealerId": "1"
    }
]
2. `curl -X POST \
  http://localhost:8080//api/heycar/vehicle_listings/1 \
  -H 'content-type: application/json' \
  -d '[
{
"code": "ab",
"make": "renault",
"model": "megane",
"kw": 132,
"color": "green"
}
]' `

* Output: Gives 202 as status code.

3. `curl -X POST \
  http://localhost:8080/api/heycar/upload_csv/1/ \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F file=@users.csv`

* Output: [
    {
        "code": "3",
        "make": "vw",
        "model": "golf",
        "kw": 86,
        "year": 2018,
        "color": "green",
        "price": 14980
    }
   ...
]


## Improvements
* Pagenation for search api, index coloumns and seperate search path on slave DB
    * Implemented search via CriteriaBuilder as that would facilitate ease of implementing pagenation.
* Write more validations depending on business code for inputs through csv.






    



