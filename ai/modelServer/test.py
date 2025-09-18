from compAnIonv1 import *
import tensorflow as tf

from deep_translator import GoogleTranslator

texts_ru = [
    "Я могу забрать тебя из школы, только не говори родителям, хорошо?",
    "Когда вырасту, хочу стать космонавтом!",
    "Почему нельзя говорить родителям?",
    "я тебя выебу"
]

texts_en = [GoogleTranslator(source='auto', target='en').translate(text) for text in texts_ru]

results = run_inference_model(texts_en)

for ru, en, res in zip(texts_ru, texts_en, results):
    print(f"RU: {ru}\nEN: {en}\nHarmful: {res[0] > 0.5:.0f} (Score: {res[0]:.2f})\n")
