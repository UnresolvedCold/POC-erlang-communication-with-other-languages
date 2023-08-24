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
    gen_tcp:send(Socket, "Hi\n"),
    Reply = receive_reply(Socket),
    io:format("Received from Java: ~s~n", [Reply]).

generate_random_floats(N) ->
    lists:map(fun(_) -> random:uniform() * 10 end, lists:seq(1, N)).

receive_reply(Socket) ->
    receive_reply(Socket, []).

receive_reply(Socket, Acc) ->
    case gen_tcp:recv(Socket, 0) of
        {ok, Data} ->
            receive_reply(Socket, [Data | Acc]);
        {error, closed} ->
            lists:reverse(Acc);
        L ->
            io:format(L)
    end.
