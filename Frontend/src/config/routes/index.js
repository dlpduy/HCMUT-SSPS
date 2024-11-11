import mainRouter from './main.routes';
import studentRouter from './student.routes';
import spsoRouter from './spso.routes';

const publicRouter = [mainRouter];
const privateRouter = [studentRouter,spsoRouter];
export { publicRouter, privateRouter };