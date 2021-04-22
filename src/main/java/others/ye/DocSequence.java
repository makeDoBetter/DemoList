package others.ye;

import org.springframework.util.StringUtils;


/**
 * @author mingbin
 * @version 1.0
 * @ClassName DocSequence
 * @description
 * @date 2019/6/11 16:20
 * @since JDK 1.8
 */
@SequenceEntity
public class DocSequence {
    private static final long serialVersionUID = 5243672831877794655L;
    @SeqNameField
    private String docType;
    @SeqNextValField
    private Long nextSeqNumber;
    private String pk1Value;
    private String pk2Value;
    private String pk3Value;
    private String pk4Value;
    private String pk5Value;
    private Long tenantId;

    public DocSequence() {
    }

    public DocSequence(String docType, Long nextSeqNumber){
        this.docType = docType;
        this.nextSeqNumber = nextSeqNumber;
    }

    public DocSequence(String docType, String pk1Value, String pk2Value, String pk3Value, String pk4Value, String pk5Value) {
        this.docType = docType;
        if (StringUtils.isEmpty(pk1Value)) {
            this.pk1Value = "-1";
        } else {
            this.pk1Value = pk1Value;
        }

        if (StringUtils.isEmpty(pk2Value)) {
            this.pk2Value = "-1";
        } else {
            this.pk2Value = pk2Value;
        }

        if (StringUtils.isEmpty(pk3Value)) {
            this.pk3Value = "-1";
        } else {
            this.pk3Value = pk3Value;
        }

        if (StringUtils.isEmpty(pk4Value)) {
            this.pk4Value = "-1";
        } else {
            this.pk4Value = pk4Value;
        }

        if (StringUtils.isEmpty(pk5Value)) {
            this.pk5Value = "-1";
        } else {
            this.pk5Value = pk5Value;
        }

    }

    public String getDocType() {
        return this.docType;
    }

    public Long getNextSeqNumber() {
        return this.nextSeqNumber;
    }

    public String getPk1Value() {
        return this.pk1Value;
    }

    public String getPk2Value() {
        return this.pk2Value;
    }

    public String getPk3Value() {
        return this.pk3Value;
    }

    public String getPk4Value() {
        return this.pk4Value;
    }

    public String getPk5Value() {
        return this.pk5Value;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public void setNextSeqNumber(Long nextSeqNumber) {
        this.nextSeqNumber = nextSeqNumber;
    }

    public void setPk1Value(String pk1Value) {
        this.pk1Value = pk1Value == null ? null : pk1Value.trim();
    }

    public void setPk2Value(String pk2Value) {
        this.pk2Value = pk2Value == null ? null : pk2Value.trim();
    }

    public void setPk3Value(String pk3Value) {
        this.pk3Value = pk3Value == null ? null : pk3Value.trim();
    }

    public void setPk4Value(String pk4Value) {
        this.pk4Value = pk4Value == null ? null : pk4Value.trim();
    }

    public void setPk5Value(String pk5Value) {
        this.pk5Value = pk5Value == null ? null : pk5Value.trim();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
