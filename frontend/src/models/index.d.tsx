

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

export interface DEPARTMENT {
    id: number,
    name: string
}
export interface declaredStateDepartment  {
    departments: DEPARTMENT[] | [],
    department: DEPARTMENT |{},
    message: string | null,
    departmentSuccess: boolean,
    departmentError: boolean
}
export interface TASK {
    id: number,
    name: string,
    location: string,
    description: string,
    department: DEPARTMENT,
    creator: USER,
    completor: USER | null,
    inProgress: boolean,
    dateCreated: string | null,
    dateUpdated: string | null,
    completed: boolean,
    cancelled: boolean,
    urgent: boolean
}

export interface TASKREQUEST {
    name: string,
    location: string,
    description: string,
    department: string,
    isUrgent: boolean
}
