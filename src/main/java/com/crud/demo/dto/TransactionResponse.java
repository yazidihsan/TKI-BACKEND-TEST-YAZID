package com.crud.demo.dto;

 

public class TransactionResponse {
    // private String kode;
    // private BigDecimal admin;
    // private BigDecimal total;

       private int status;
        private String message;
        private Object data;

    
    public TransactionResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public Object getData() {
            return data;
        }
        public void setData(Object data) {
            this.data = data;
        }
    // public String getKode() {
    //     return kode;
    // }
    // public void setKode(String kode) {
    //     this.kode = kode;
    // }
    // public BigDecimal getAdmin() {
    //     return admin;
    // }
    // public void setAdmin(BigDecimal admin) {
    //     this.admin = admin;
    // }
    // public BigDecimal getTotal() {
    //     return total;
    // }
    // public void setTotal(BigDecimal total) {
    //     this.total = total;
    // }


}
