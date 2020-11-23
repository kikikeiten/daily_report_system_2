package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Idea;

public class IdeaValidator {
    public static List<String> validate(Idea i) {

        List<String> errors = new ArrayList<String>();

        String titleError = _validateTitle(i.getTitle());

        if (!titleError.equals("")) {
            errors.add(titleError);
        }

        String contentError = _validateContent(i.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }

        return errors;
    }

    private static String _validateTitle(String title) {
        if (title == null || title.equals("")) {
            return "タイトルを入力してください。";
        }

        return "";
    }

    private static String _validateContent(String content) {
        if (content == null || content.equals("")) {
            return "内容を入力してください。";
        }

        return "";
    }
}