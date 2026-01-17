import http from "http";
import { generate_question } from "./generation.js";

const PORT = process.env.PORT || 3000;
let today_question = "";
let date_message = new Date();

// async callback => await the response from generate_text
const server = http.createServer(async (req, res) => {
  res.setHeader("Content-type", "application/json");

  if (req.url === "/api" && req.method === "GET") {
    // check for the next day, if it's another month...
    try {

      if (today_question === "" || new Date().getDay() < date_message.getDay()) {
        today_question = await generate_question("Make a joke");
        date_message = new Date();
      }
      let response = {
        "question": JSON.stringify(today_question)
      }
      res.write(JSON.stringify(response))
      console.log(date_message.toDateString());
    }
    catch (err) {
      res.statusCode = 500;
      res.end(JSON.stringify({ error: err.message }));
    }
  }


  res.end();

});


server.listen(PORT, () => {
  console.log("Server running on port " + PORT);
});

