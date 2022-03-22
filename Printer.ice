module Demo
{
    interface Printer
    {
        string printString(string s);
    }

    struct TimeOfDay
    {
        short hour;         // 0 - 23
        short minute;       // 0 - 59
        short second;       // 0 - 59
    }

    interface Clock
    {
        TimeOfDay getTime();
        void setTime(TimeOfDay time);
    }

    struct LoginResponse
    {
        int code;
    }

    struct LoginRequest
    {
        string userName;
        string password;
    }

    interface LoginService
    {
        LoginResponse login(LoginRequest request);
    }
}