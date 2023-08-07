package com.example;

import com.ericsson.otp.erlang.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class HelloWorld {
    public boolean isAlive() {
        return true;
    }

    public static void main(String[] args) throws Exception {
        OtpNode node = new OtpNode("java_node");
        OtpMbox mbox = node.createMbox("java_mailbox");
        // System.out.println("Node Created. Now, you can communicate with this node.");

        Sample evaluator = new Sample();

        while (true) {
            try {
                OtpErlangTuple erlTuple = (OtpErlangTuple) mbox.receive();

                // System.out.println("Received: " + erlTuple);
                OtpErlangPid fromPid = (OtpErlangPid) erlTuple.elementAt(0);
                OtpErlangList erlList = (OtpErlangList) erlTuple.elementAt(1);

                // Process the input list erlList
                List<Double> inputValues = new ArrayList<>();
                for (OtpErlangObject obj : erlList.elements()) {
                    if (obj instanceof OtpErlangDouble) {
                        OtpErlangDouble erlFloat = (OtpErlangDouble) obj;
                        inputValues.add(erlFloat.doubleValue());
                    }
                }

                // Perform evaluation using inputValues and get the result
                double result = evaluator.evaluate(inputValues);

                // Create the response tuple and send it back to Erlang
                OtpErlangAtom replyAtom = new OtpErlangAtom("ok");
                OtpErlangDouble resultDouble = new OtpErlangDouble(result);
                OtpErlangObject[] replyElements = { replyAtom, resultDouble };
                OtpErlangTuple replyTuple = new OtpErlangTuple(replyElements);
                mbox.send(fromPid, replyTuple);

                // Wait for an ok message to continue
                OtpErlangTuple okRes = (OtpErlangTuple) mbox.receive();
                // System.out.println("Received: " + okRes);
                OtpErlangAtom res = (OtpErlangAtom) okRes.elementAt(1);
                if (!res.toString().equals("ok")) break;

            } catch (Exception e) {
                break;
            }
        }
    }
}
