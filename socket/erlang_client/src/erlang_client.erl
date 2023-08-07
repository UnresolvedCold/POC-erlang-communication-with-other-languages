-module(erlang_client).
-export([start/0, send/1, close/1]).

start() ->
    {ok, Socket} = gen_tcp:connect("127.0.0.1", 12345, []),
    Socket.

close(Socket) ->
    gen_tcp:close(Socket).

send(Socket) ->
    Floats = generate_random_floats(5),
    FloatsJSON = jsx:encode(Floats), 
    gen_tcp:send(Socket, "Hi\n").

generate_random_floats(N) ->
    lists:map(fun(_) -> random:uniform() * 10 end, lists:seq(1, N)).
