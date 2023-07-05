import { ACTION, declaredStateDepartment } from "../../models/index.d" 


let initialState = {
    departments:  [],
    department: {},
    message: null,
    departmentSuccess: false,
    departmentError: false
}

export default (state: declaredStateDepartment = initialState, action: ACTION) => {
    switch(action.type) {
        case "get_departments":
            return {
                ...state,
                departments: action.payload,
                departmentSuccess: true
            }
        case "department_RESET":
            return {
                ...state,
                message: action.payload,
                departmentError: false
            }
        case "department_RESET": 
            return {
                ...state,
                message: null,
                departmentSuccess: false,
                departmentError: false
            }
        default:
            return state
    }
}