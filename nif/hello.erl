-module(hello).
-on_load(load_nif/0).

load_nif() ->
    erlang:load_nif("hello", 0).

