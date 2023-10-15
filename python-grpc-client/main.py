import grpc

import user_pb2 as pb2
import user_pb2_grpc as pb2_grpc

if __name__ == '__main__':
    host = 'localhost'
    server_port = 50051
    channel = grpc.insecure_channel(
        '{}:{}'.format(host, server_port))
    stub = pb2_grpc.UserServiceStub(channel)
    request = pb2.UserRequest(username="christian")
    response = stub.getUserDetails(request)
    print(f'{response}')
    channel.close()
