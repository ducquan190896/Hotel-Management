

export interface USER {
    id: number,
    username: string,
    firstname: string,
    surename: string,
    roles: string[],
}

export interface declaredStateUser  {
    authUser: USER | {},
    users: USER[] | [],
    userUpdateStatus: boolean,
    userUpdated: USER | {},
    message: string | null,
    authSuccess: boolean,
    authError: boolean
}

export interface ACTION {
    type: string,
    payload?: any
}

export interface LoginForm {
    username: string,
    password: string
}

export interface UserRegisterForm {
    username: string,
    firstname: string,
    surename: string,
    password: string,
}

export interface CHANGEPASSWORD {
    currentPassword: string,
    newPassword: string,
    confirmPassword: string
}
