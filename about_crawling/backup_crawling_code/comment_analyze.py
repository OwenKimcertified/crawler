from transformers import BertTokenizer, BertModel
from scipy.spatial.distance import cosine
import numpy as np
import torch


tokenizer = BertTokenizer.from_pretrained('bert-base-multilingual-cased')
model = BertModel.from_pretrained('bert-base-multilingual-cased')

def get_cls_embedding(text):
    inputs = tokenizer(text, return_tensors = 'pt', padding = True, truncation = True, max_length = 128)
    with torch.no_grad():
        outputs = model(**inputs)
    last_hidden_states = outputs.last_hidden_state
    cls_embedding = last_hidden_states[:, 0, :].squeeze()
    return cls_embedding

text1 = "사무용입니다"
text2 = "밧데리가 2시간도 안됍니다 사무실용 입니다"

embedding1 = get_cls_embedding(text1)
embedding2 = get_cls_embedding(text2)

def euclidean_distance(vec1, vec2):
    return np.linalg.norm(vec1 - vec2)

similarity = 1 - cosine(embedding1.numpy(), embedding2.numpy())

print("Cosine Similarity:", similarity)

euclidean_distance = euclidean_distance(embedding1.numpy(), embedding2.numpy())
print("Euclidean Distance:", euclidean_distance)