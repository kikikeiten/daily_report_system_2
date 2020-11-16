package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Member;
import utils.DBUtil;

public class MemberValidator {
    public static List<String> validate(Member m, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String code_error = _validateCode(m.getCode(), code_duplicate_check_flag);
        if (!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = _validateName(m.getName());
        if (!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(m.getPassword(), password_check_flag);
        if (!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

    // メンバーid
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        // 必須入力チェック
        if (code == null || code.equals("")) {
            return "Member idを入力してください。";
        }

        // すでに登録されているmember idとの重複チェック
        if (code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long checkRegisteredCode = (long) em.createNamedQuery("checkRegisteredCode", Long.class)
                    .setParameter("code", code)
                    .getSingleResult();
            em.close();
            if (checkRegisteredCode > 0) {
                return "入力されたmember idの情報はすでに存在しています。";
            }
        }

        return "";
    }

    // Member nameの必須入力チェック
    private static String _validateName(String name) {
        if (name == null || name.equals("")) {
            return "氏名を入力してください。";
        }

        return "";
    }

    // パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // パスワードを変更する場合のみ実行
        if (password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}