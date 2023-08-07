-module(erlang_client).
-export([start/0]).

start() ->
    {ok, Socket} = gen_tcp:connect("127.0.0.1", 12345, [{active, false}]),
    io:format("Connected to Java server. Enter a message: "),
    Message = io:get_line(""),
    gen_tcp:send(Socket, Message),
    {ok, Response} = gen_tcp:recv(Socket, 0),
    io:format("Response from Java server: ~s~n", [Response]),
    gen_tcp:close(Socket).