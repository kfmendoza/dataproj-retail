var express = require("express");
var app = express();


app.listen(3000, () => {
 console.log("Server running on port 3000");
});
app.get("/randomPurchase", (req, res, next) => {
 res.json(["Tony","Lisa","Michael","Ginger","Food"]);
});

var casual = require("casual");
casual.define('user', function() {
	return {
		headers: {
			session: {
				id: casual.uuid
			},
			ip: casual.ip
		},
		payload: {
			context: {
				customer: {
					id: "generate"
				}
			}		
			events: [
			{
				"id": "generate",
				"timestamp": 1234
				"type": "purchase_success"
				"fields": [
					{
						"key": "generate",
						"value": "generate"
					}
				]
			}
	}
			
			
	};
});

// Generate object with randomly generated fields
var user = casual.user;
console.log(JSON.stringify(user))
// Generate random sentence
// You don't need function call operator here
// because most of generators use properties mechanism
var sentence = JSON.stringify(casual.sentence);;
console.log("Random sentence" + sentence);