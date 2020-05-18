var express = require("express");
var app = express();
var casual = require("casual");

app.listen(3000, () => {
 console.log("Server running on port 3000");
});
app.get("/random", (req, res, next) => {
 casual.define('log', function() {
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
					id: casual.numerify('#####')
				}
			},		
			events: [
			{
				id: casual.uuid,
				timestamp: casual.numerify('##########'),
				type: "purchase_success",
				fields: [
					{
						key: "fake",
						value: "fake"
					}
				]
			}
			]
		}			
	};
 });
 var log = casual.log;
 res.json(log);
});




