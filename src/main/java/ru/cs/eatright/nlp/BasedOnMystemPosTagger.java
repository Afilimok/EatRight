package ru.cs.eatright.nlp;

import java.util.List;

import java.io.ByteArrayOutputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.util.ArrayList;

public class BasedOnMystemPosTagger {
    private static final Logger logger = LoggerFactory.getLogger(BasedOnMystemPosTagger.class);


    //private static final String MYSTEM_COMMAND = "/Users/alina/Desktop/csc/eatright/eatRight/src/main/resources/mystem -idln --format=json -";

    private static final String MYSTEM_COMMAND = "/home/alina/projects/mystem/mystem -idln --format=json -";
    private static ObjectMapper mapper = new ObjectMapper();
/*
    // todo: Debug example
    public static void main(final String[] args) {
        BasedOnMystemPosTagger posTagger = new BasedOnMystemPosTagger();

        final String text = "хочу скушать итальянскую пиццу и закусить черешней с пятью котлетами по-киевски";
        List<String> result = posTagger.posTag(text);
        for (String s : result) {
            System.out.println(s);
        }
    }*/

    private String execToString(String text) throws Exception {
        String command = String.format("echo \"%s\" | %s", text, MYSTEM_COMMAND);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine commandline = new CommandLine("/bin/sh");
        commandline.addArguments("-c");
        commandline.addArguments("'" + command + "'", false);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        exec.setStreamHandler(streamHandler);
        exec.execute(commandline);
        return outputStream.toString();
    }

    public ArrayList<String> posTag(String text) {
        try {
            String mystemResult = String.format("[%s]",execToString(text));
            logger.info("mystemResult = {}", mystemResult);
            System.out.println(mystemResult);
            mystemResult = mystemResult.replaceAll("\\}\n\\{", "},{");
            JsonNode node = mapper.readValue(mystemResult, JsonNode.class);

            ArrayList<String> posTags = new ArrayList<>();
            for (JsonNode parsedWord : node) {
                JsonNode wordProps = parsedWord.get("analysis").get(0);
                if (wordProps != null) {
                    String grammems = wordProps.get("gr").asText();
                    if (grammems != null) {
                        String posTag = grammems.split("=")[0];
                        posTags.add(posTag.split(",")[0]);
                    }
                }
            }
            return posTags;
        } catch (Exception e) {
            logger.error("Exception occur during parsing text pos-tagging.", e);
            return null;
        }
    }
}
