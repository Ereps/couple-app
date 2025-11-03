
const endpoint = "https://models.github.ai/inference/chat/completions";
const model = "openai/gpt-4o";
// const model = "deepseek/DeepSeek-V3-0324";
const key = process.env.API_KEY;

export async function generate_question(question) {
  return get_llm_message(await generate_text(question));
}

function get_llm_message(json_response) {

  try {
    return json_response.choices[0].message.content;
  } catch (err) {
    console.log(err.message);
    console.log(json_response);
    return ""
  }
}



async function generate_text(prompt) {
  const req = await fetch(endpoint, {
    method: 'Post',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + key,
    },
    body: JSON.stringify({
      model: model,
      messages: [
        { role: 'user', content: prompt }
      ]
    })
  });
  const raw_result = await req.text();
  let json_result;
  try {

    json_result = JSON.parse(raw_result);
    return json_result;

  } catch (err) {
    return { err: "can't parse the response : " + raw_result };

  }

}
