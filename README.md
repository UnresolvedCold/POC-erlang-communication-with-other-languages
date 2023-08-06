# Introduction

This repository mainly deals with the prediction of the transit time model based on the `pmml` file.

This repo will serve as a POC on the efficiency of using different methodologies that can be used for this approach.

# Objective

Erlang doesn't has a `pmml` file support. 
But we want to enable all the teams using Erlang to support the predictions via the AI/ML models.
So we need to create a wrapper in erlang that will hide the complexity of using any other (more efficient) language to provide the solution.

# Approaches

1. Use `Python` to provide the solution and communicate this result via `erlport`.
2. Use `Java` to provide the solution and use `Jinterface` to simulate the app as a `Erlang` node.
3. Use `Rust` (if there is a `PMML` parser) and integrate Erlang using Erlang `NIF`.
4. Use `C/C++` to provide the library using (.so, .dylib or .dll) files.
5. Run a server that will communicate the result via `socket`. 
   1. We can do this using `REST` protocol (which will be slow)
   2. We will also look into `gRPC` protocol and compare the efficiency wrt `REST`. 