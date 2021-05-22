package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Member;
import utils.DBUtil;

public class MemberValidator {
    public static List<String> validate(Member m, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {

        List<String> errors = new ArrayList<String>();

        String codeError = _validateCode(m.getCode(), codeDuplicateCheckFlag);

        if (!codeError.equals("")) {
            errors.add(codeError);
        }

        String nameError = _validateName(m.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        String passwordError = _validatePassword(m.getPassword(), passwordCheckFlag);
        if (!passwordError.equals("")) {
            errors.add(passwordError);
        }

        return errors;
    }

    // メンバーIDのバリデーション
    private static String _validateCode(String code, Boolean codeDuplicateCheckFlag) {
        // 必須入力チェック
        if (code == null || code.equals("")) {
            return "Please enter your member ID.";
        }

        // すでに登録されているメンバーIDとの重複チェック
        if (codeDuplicateCheckFlag) {
            EntityManager em = DBUtil.createEntityManager();

            long checkRegisteredCode = (long) em.createNamedQuery("checkRegisteredCode", Long.class)
                    .setParameter("code", code)
                    .getSingleResult();
            em.close();

            if (checkRegisteredCode > 0) {
                return "The member ID you entered already exists.";
            }
        }
        return "";
    }

    // メンバー名の必須入力チェック
    private static String _validateName(String name) {
        if (name == null || name.equals("")) {
            return "Please enter your name.";
        }
        return "";
    }

    // パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean passwordCheckFlag) {

        // パスワードを変更する場合のみ実行
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return "Please enter your password.";
        }
        return "";
    }
}
