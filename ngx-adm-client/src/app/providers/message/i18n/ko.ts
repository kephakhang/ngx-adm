import { environment } from "../../../../environments/environment"

export const ko = {
    lang: "Korean",
    global: {
        ok: "Ok",
        error: "Error",
        confirm: "Confirm",
        cancel: "Cancel",
        close: "Close",
        notification: "Notification"
    },
    auth: {

        passwordRequired: "Password is Required",
        passwordLengthRange: `Password should contain from ${environment.password.min} to ${environment.password.min} characters`,
        passwordMismatch: "Two password are mimatched.",
        passwordLengthError: `Password should contain from ${environment.password.min} to ${environment.password.max} characters`,
        passwordRuleError: `The password must be between ${environment.password.min} and ${environment.password.max}, at least one uppercase letter, at least one lowercase letter, at least one number, and at least one special[!@#$&*] character.`,
        login: {
            greeting: "Hello! Log in with your email or mobile.",
            greetingEmailOnly: "Hello! Log in with your email.",
            idShouldBeReal: "Email or Mobile should be the real one.",
            emailShouldBeReal: "Email should be the real one.",
            mobileShouldBeReal: "Mobile should be the real one.",
            idRequired: "Email or Mobile is required.",
            emailRequired: "Eamil is required",
            noAccount: "Don't have an account? ",
            unknownError: "Unknow error occurs when login, please contact the administrator."

        },
        logout: {

        },
        register: {
            unknownError: "unknown error in signup",
            nameInvalid: "Invalid Name.",
            emailShouldBeReal: "Email should be the real one.",
            mobileShouldBeReal: "Email should be the real one.",
            emailRequired: "Email is required",
            mobileRequired: "Mobile is required",
            termsNotSelected: "You have to read terms & conditions and select checkbox for the agreement.",
            nameLengthError: `Name should contain from ${environment.name.min} to ${environment.name.max} characters`,
            success: "User registraion is done successfully."

        },
        request: {

        },
        reset: {

        }
    },
    filetransfer: {
        descNeeded: "Description is needed when file transfer"
    }
}