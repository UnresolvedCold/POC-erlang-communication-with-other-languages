-module(wrapper).
-export([init/0, get_required_features/1, evaluate/2, stop/1]).

init() ->
  PythonCodePath = code:priv_dir(aiml_model_wrapper),
  {ok, P} = python:start_link([{python_path, PythonCodePath}, {python, "/Users/shubham.kumar/Projects/Extras/erlport/aiml_model_wrapper/venv/bin/python"}]),
  python:call(P, program, init, []),
  P.

get_required_features(P) ->
  python:call(P, program, get_required_features, []).

evaluate(P, Features) ->
  SerializedFeatures = jiffy:encode(Features),
  python:call(P, program, evaluate, [SerializedFeatures]).

stop(P) ->
  python:stop(P).
