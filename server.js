import http from "http";
import { generate_question } from "./generation.js";

const PORT = process.env.PORT || 3000;
let today_question = "";
let date_message = new Date();
let prompt = `Tu es un générateur de questions originales pour couples. 
Ta mission est de proposer ** une seule question à la fois **, qui soit ** unique, amusante, profonde ou surprenante **, et qui incite le dialogue entre deux partenaires. 
Évite absolument les répétitions par rapport aux questions précédentes. 
Varie les thèmes: émotions, souvenirs, rêves, relations, humour, défis, intimité, créativité, valeurs personnelles, projets futurs. 
Ne pose jamais de questions vagues ou génériques comme Comment ça va ? ou Qu'est-ce que tu aimes ?.
Répond uniquement par la question, ** sans explications ni listes **.`;

// async callback => await the response from generate_text
const server = http.createServer(async (req, res) => {
  res.setHeader("Content-type", "application/json");

  if (req.url === "/api" && req.method === "GET") {
    // check for the next day, if it's another month...
    try {

      //INFO: Removing the condition for debug
      // if (today_question === "" || new Date().getDay() < date_message.getDay()) {
      //   date_message = new Date();
      // }
      today_question = await generate_question(prompt);
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

