import { createStore, applyMiddleware, combineReducers } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';

import thunk from 'redux-thunk';
import userReducer from './reducers/userReducer';
import DepartmentReducer from './reducers/DepartmentReducer';


export const HOST_URL= "http://192.168.0.103:8080";
const initialState= {};

const rootReducer = combineReducers({
    USERS: userReducer,
    DEPARTMENTS: DepartmentReducer
});

const middleware = [thunk];

const store = createStore(
    rootReducer,
    initialState,
   
    composeWithDevTools(applyMiddleware(...middleware))
)


export default store;
// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch