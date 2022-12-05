package SumServer;

import GreetingServer.GreetingServiceImpl;
import com.proto.sum.Sum;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SumServiceImpl extends SumServiceGrpc.SumServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {

        Sum sum = request.getSum();
        double num1 = sum.getNum1();
        double num2 = sum.getNum2();

        double result = num1 + num2;
        SumResponse response = SumResponse.newBuilder()
                .setResult(result)
                .build();


        System.out.println("Server Output: " + num1 + " + " + num2 + " = " + result);

        responseObserver.onNext(response);

        responseObserver.onCompleted();

    }

}
