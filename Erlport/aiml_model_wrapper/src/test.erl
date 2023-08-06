-module(test).
-export([stress_test/0]).
-import(wrapper, [init/0, get_required_features/1, evaluate/2, stop/1]).

get_features_somehow() ->
  RandomFloat = fun() -> rand:uniform() * 10 end,
  #{idc_time => RandomFloat(),
    x_start => RandomFloat(),
    y_start => RandomFloat(),
    x_goal => RandomFloat(),
    y_goal => RandomFloat()}.

stress_test() ->
  P = init(),
  NumRuns = 200000,
  NumExcludedRuns = 10000, % Number of runs to exclude
  TotalRuns = NumRuns + NumExcludedRuns,
  ElapsedTimes = evaluate_runs(P, TotalRuns, [], NumExcludedRuns),
  stop(P),
  
  ExcludedTimes = tl(ElapsedTimes), % Remove the first NumExcludedRuns times
  TotalTime = lists:sum(ExcludedTimes),
  AverageTime = TotalTime / (NumRuns - NumExcludedRuns), % Calculate average for excluded runs
  io:format("Average time per evaluation (excluding first ~p runs): ~p milliseconds~n", [NumExcludedRuns, AverageTime]).
  
evaluate_runs(_, 0, Acc, _) ->
  lists:reverse(Acc);
evaluate_runs(P, N, Acc, NumExcludedRuns) ->
  case N > NumExcludedRuns of
    true ->
      StartTime = erlang:monotonic_time(millisecond),
      Features = get_features_somehow(), % Replace this with your feature generation logic
      evaluate(P, Features),
      EndTime = erlang:monotonic_time(millisecond),
      ElapsedTime = EndTime - StartTime,
      evaluate_runs(P, N - 1, [ElapsedTime | Acc], NumExcludedRuns);
    false ->
      Features = get_features_somehow(),
      evaluate(P, Features),
      evaluate_runs(P, N - 1, Acc, NumExcludedRuns)
  end.
