# Day 5 notes: REST APIs, Postman and what to automate

## Introduction to TOS REST API (Tufin, 16 slides)

**What an API and REST are**
- An API lets one program use features of another program. Tufin APIs let clients automate security policy tasks and put them into their own apps and portals.
- REST is an architectural style for web services (also called RESTful web services). The things a REST API works with are called resources.
  - SecureTrack resources: devices, rules, network_objects, domains.
  - SecureChange resources: tickets, workflow steps.
  - SecureApp resources: applications, servers, customers.
- REST uses HTTP or HTTPS. The client sends an HTTP request with identifying information (for example a resource ID). The server sends back a Data Transfer Object (DTO) with the information. In the deck, `https://10.100.122.198/securetrack/api/devices/25` returns the JSON of device 25.

**How a Tufin URL is structured**
Example: `https://192.168.1.1/securetrack/api/devices/20/rules/16373/documentation`. It has 3 sections:

| Section | What it is | In the example |
|---|---|---|
| Prefix | `https://` plus the IP address of the TOS server | `https://192.168.1.1` |
| Application | The product the API belongs to | `/securetrack/api/` |
| Resource | The resource ID plus any more path parameters | `devices/20/rules/16373/documentation` |

The application part is `/securetrack/api/` for SecureTrack, `/securechangeworkflow/api/securechange/` for SecureChange and `/securechangeworkflow/api/secureapp/` for SecureApp.

URI, URL and URN: a URI identifies a specific resource. A URL is a URI that includes the protocol (`https://...`). A URN is a URI without a protocol (`urn:issn:1082-9873`). All Tufin REST APIs start with `https://`, so they can be called URLs.

**Path parameters vs query parameters**

| | Path parameter | Query parameter |
|---|---|---|
| Where | Inside the path, as part of the resource | After a `?` at the end of the URL |
| What it does | Says WHICH resource | Filters, searches or limits what I get back |
| Format | A value in a place of the path | `name=value`, and many are split by `&` |
| Tufin example | `.../devices/20/rules/16373/documentation`: 20 is the device id and 16373 is the rule id | `.../network_objects/search?filter=text&name=host&exact_match=true`: 3 parameters (`filter`, `name`, `exact_match`) |

- Pagination uses 2 optional query parameters: `start` (zero based, so `start=1` is the second element) and `count` (how many to return). `.../devices/254/rules?start=50&count=10` returns rules 51 to 60. Most APIs also return a `total` field, so the client knows how many calls it needs.
- The same URL with a different HTTP method means something different: GET gets the rule documentation, PUT changes it, DELETE removes it.

**HTTP methods**
GET reads a resource (read only). POST creates a new resource. PUT changes an existing resource. PATCH changes some details of an existing resource. DELETE removes a resource. POST and PUT need data from the client, called the body, in XML or JSON.

**What each status code means (slide 13)**

| Code | Meaning in the deck |
|---|---|
| 200 OK | Returned for GET. The data was successfully retrieved. |
| 201 Created | Returned for POST. The resource was successfully added. |
| 204 No Content | Returned for PUT and DELETE. The resource was successfully modified or deleted. |
| 4XX | The call failed because of a client error. |
| 5XX | The call failed because of a server error. |

The deck says the exact error codes of each API call are in the TOS REST API documentation.

**Other things in the deck**
- A GET can be sent from a browser address bar (it asks for a username and password). Adding `.json` to the URL gives JSON instead of the default XML. With query parameters, `.json` goes before the `?`.
- For POST, PUT and DELETE I need a REST client. Tufin gives a ZIP of Postman collections for its APIs.
- The API documentation is on the TOS machine (`/securetrack/apidoc/` and `/securechangeworkflow/apidoc/`) and online in the Tufin Knowledge Center. It can be searched with the expand buttons and Ctrl+F.

## What I saw in the Toolshop API (Postman requests)
Base URL: `https://api.practicesoftwaretesting.com`. I used the Swagger docs (`/docs`) for the endpoints.

| Request | Status | What came back |
|---|---|---|
| `GET /brands` | 200 | A plain list (a JSON array), with `id`, `name` and `slug` for each brand. No `data` inside. Today it has 2 brands. |
| `GET /products` | 200 | An object, not a list. The products are in `data`, with `current_page`, `per_page` (9), `total` (50), `last_page` (6) around it. |
| `GET /products/{id}` with a real id from the list | 200 | The one product (for example Combination Pliers, price 14.15). |
| `GET /products/{id}` with a made-up id | 404 | `{"message":"Requested item not found"}` |
| `POST /users/login` with the demo customer | 200 | `{"access_token": "...", "token_type": "bearer", "expires_in": 300}`. The `access_token` is at the top level of the body. It is a JWT (3 parts split by dots). |
| `POST /users/login` with an email that does not exist | 401 | `{"error":"Unauthorized"}` |
| `GET /products/search?q=pliers` | 200 | The same page object as `/products`, with the 4 pliers products. `q` is a query parameter, and `{id}` above is a path parameter. |

The API reseeds its data: the IDs of the brands and products changed between my afternoon and evening requests on the same day (the names, prices and structure stayed the same). So an ID can never be written in a test.

Also found: a login with a missing password, an empty body or bad JSON gives 401 with `{"error":"Invalid login request"}`, which is a different message from an unknown email. A search with no match gives 200 and an empty `data` list. `GET /products?page=999` gives 200 and an empty list.

**Compared with the Tufin deck:** Tufin uses `start` and `count` for pages and a `total` field. Toolshop uses `page` and gives `total` too. Both put the resource id in the path and filters in the query. Both use 200 for a good GET and 4XX for a client mistake.

## Which Day 1 test cases to automate
The 18 product search cases are TF40-31 to TF40-48 (UI cases on with-bugs.practicesoftwaretesting.com). To decide, I called the same behavior on the API, on the clean API and on the with-bugs API. A case is worth automating on the API if the API can show the behavior, and if checking it by hand every time is slow.

| Test case | Decision | Why |
|---|---|---|
| TF40-31 Search "pliers" returns the 4 pliers products | Automate | The API gives the 4 products with `q=pliers`, and the count and names can be asserted. |
| TF40-32 Search in upper case gives the same results | Automate | The same call with `PLIERS` gives the same 4 products. It is a cheap comparison. |
| TF40-33 Search part of a word ("plie") | Automate | `q=plie` gives the same 4 products. |
| TF40-34 Spaces around the term are ignored | Automate | The API also trims the spaces: `q=%20%20pliers%20%20` gives the 4 products. |
| TF40-35 A term that matches nothing | Automate | `q=zzzz` gives 200 with an empty `data` list and `total` 0. |
| TF40-36 Only spaces finds nothing | Automate | `q=` with 3 spaces gives an empty list. |
| TF40-37 Fewer than 3 characters is blocked | Keep manual | This is a rule of the Search box and button. The API does not block it: `q=ab` returns 3 products. |
| TF40-38 Search with an empty box keeps the full list | Keep manual | This is what the page does with an empty box. The API has no such behavior (an empty `q` gives an empty list). |
| TF40-39 A ">" character is handled as plain text | Automate | The with-bugs API gives 500 "Something went wrong" for `saw>`. The clean API gives 200. A status plus content check catches this bug. |
| TF40-40 "%" characters are handled as plain text | Automate | The with-bugs API returns all 29 products for `%%%`. The clean API returns none. An API check catches it. |
| TF40-41 The X button clears the search | Keep manual | Only a button on the page. There is no API behind it. |
| TF40-42 Clicking a result opens the product page | Keep manual | It is page navigation. The API part is only `GET /products/{id}`, which is covered by other API tests. |
| TF40-43 The button is labelled "Search" | Keep manual | A text on the screen (the bug TF40-54 was "Serch"). An API cannot see it. |
| TF40-44 More than 9 results are split into pages | Automate | The API shows `per_page` 9, `total` and `page=2` with the other products. |
| TF40-45 Every result card shows a photo | Keep manual | Showing a picture is visual. The API has the file name in `product_image`, but a card without a photo (bug TF40-53) was not visible in the API data. |
| TF40-46 Sort price low to high | Automate | `sort=price,asc` can be checked for the order of the prices. Note: the UI bug TF40-50 was not in the API (the API sorted correctly), so the screen check is still done by hand. |
| TF40-47 Sort price high to low | Automate | Same with `sort=price,desc`. Same note as above. |
| TF40-48 Nothing above the price range maximum | Automate | `between=price,1,100` returns only prices up to 100. The API was correct on with-bugs too, so the UI bug TF40-51 is on the screen only. |

Result: 12 to automate, 6 to keep manual. What I learned: API tests are best for data rules (what is returned, counts, errors). They cannot see what the page shows. Two of my UI bugs (">" and "%%%") were visible at API level, and four (sort, price range, missing photo, button label) were not.

## How I wrote the six API test cases
They are in Jira as TF40-73 to TF40-78 (story TF40-70, preconditions TF40-71 and TF40-72), in the same Xray style as my earlier cases: one action in each step, test data, and one expected result that can be seen. Each is automated as one test in `day5/toolshop-api-tests`.
