package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class SystemQaDto extends AbstractBusinessDto {
    private String answer;

    private String question;

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

}
