from fastapi import FastAPI, Request
from modelServer.compAnIonv1 import run_inference_model
from deep_translator import GoogleTranslator


app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "Use POST /predict"}

@app.post("/predict")
async def predict(request: Request):
    data = await request.json()
    user_input = data["data"]["input"]
    input_list = [str(x) for x in user_input]
    texts_en = [GoogleTranslator(source='auto', target='en').translate(text) for text in input_list]
    prediction = run_inference_model(texts_en)
    results = []
    for ru, en, res in zip(input_list, texts_en, prediction):
        results.append({
            "ru": ru,
            "en": en,
            "harmful": bool(res[0] > 0.5),
            "score": round(float(res[0]), 2)
        })
    return {"prediction": results}
