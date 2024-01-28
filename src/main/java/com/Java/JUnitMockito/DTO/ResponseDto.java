package com.Java.JUnitMockito.DTO;

public class ResponseDto<T> {
	private String status;
	private String message;
	private T data;
	
	public ResponseDto() {
    }

	private ResponseDto(Builder<T> builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
	
	public static class Builder<T> {
        private String status;
        private String message;
        private T data;

        public Builder<T> status(String status) {
            this.status = status;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseDto<T> build() {
            return new ResponseDto<>(this);
        }
    }

}
