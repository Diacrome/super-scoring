import {applyMiddleware, createStore, Store} from "redux";
import {rootReducer} from "./reducers";
import thunk from "redux-thunk";

export const store: Store = createStore(rootReducer, applyMiddleware(thunk));
