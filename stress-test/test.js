import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 20000,
  duration: '60s',
};

export default function () {
  const headers = {
    'Content-Type': 'application/json',
    'X-DS': 'tenant1',
  };

  let res = http.post('http://localhost:8080/elsql/person?name=GustavoTeste', null, { headers } );
  check(res, {
    'status 200': r => r.status === 200,
  });
}
