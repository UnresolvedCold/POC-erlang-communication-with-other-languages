from pypmml import Model
import pandas as pd
import random
import json

model = None

def init():
  global model
  model = Model.fromFile('./priv/test.pmml')
  return model != None

def get_required_features():
  global model
  return model.inputNames

def evaluate(features):
  global model
  deserialized_features = json.loads(features)
  return model.predict(deserialized_features)['predicted_y']
