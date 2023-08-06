-module(client).
-export([start/0]).

start() ->
    NumRuns = 200000,
    ExcludedRuns = 10000,
    TotalTime = run_multiple_times(NumRuns, ExcludedRuns),
    AverageTime = TotalTime / (NumRuns - ExcludedRuns),

    io:format("Total time for ~p runs (excluding first ~p runs): ~p microseconds~n", [NumRuns, ExcludedRuns, TotalTime]),
    io:format("Average time per run (excluding first ~p runs): ~p microseconds~n", [ExcludedRuns, AverageTime]).

run_multiple_times(NumRuns, ExcludedRuns) ->
    run_multiple_times(NumRuns, ExcludedRuns, 0).

run_multiple_times(0, _ExcludedRuns, TotalTime) ->
    TotalTime;
run_multiple_times(N, ExcludedRuns, TotalTime) ->
    StartTime = now(),
    Values = generate_random_floats(5),
    {java_mailbox, 'java_node@GGN002262'} ! {self(), Values},  % Send the input values
    receive
        {ok, Result} -> % Wait for the response with the result
            EndTime = now(),
            ProcessingTime = timer:now_diff(EndTime, StartTime),
            % io:format("Received result: ~p~n", [Result]),
            {java_mailbox, 'java_node@GGN002262'} ! {self(), ok},
            run_multiple_times(N - 1, ExcludedRuns, TotalTime + ProcessingTime);
        _ ->
            % io:format("Something's wrong"),
            run_multiple_times(N, ExcludedRuns, TotalTime)
    end.

generate_random_floats(N) ->
    lists:map(fun(_) -> random:uniform() * 10 end, lists:seq(1, N)).
