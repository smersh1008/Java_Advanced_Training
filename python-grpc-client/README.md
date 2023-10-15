## Install gRPC tools
pip install grpcio-tools

## Compile ./proto/user.proto file
python -m grpc_tools.protoc -I./proto --python_out=. --pyi_out=. --grpc_python_out=. ./proto/user.proto

## Run ./main.py
python ./main.py