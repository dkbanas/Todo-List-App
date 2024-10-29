package com.dkbanas.todo.Shared;

public class AppExceptions {
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class TodoListAlreadyExistsException extends RuntimeException {
        public TodoListAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class TodoListNotFoundException extends RuntimeException {
        public TodoListNotFoundException(String message) {
            super(message);
        }
    }

    public static class TodoListUpdateException extends RuntimeException {
        public TodoListUpdateException(String message) {
            super(message);
        }
    }

    //   Items
    public static class TodoItemNotFoundException extends RuntimeException {
        public TodoItemNotFoundException(String message) {
            super(message);
        }
    }

        public static class TodoItemBelongsToListException extends RuntimeException {
            public TodoItemBelongsToListException(String message) {
                super(message);
            }
        }

        public static class TodoItemRequestNullException extends RuntimeException {
            public TodoItemRequestNullException(String message) {
                super(message);
            }
        }
}
