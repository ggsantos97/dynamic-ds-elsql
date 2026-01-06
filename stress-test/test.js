import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 500 },   // ramp-up inicial
    { duration: '30s', target: 2000 },  // carga máxima
    { duration: '20s', target: 500 },   // início do ramp-down
    { duration: '10s', target: 0 },     // finaliza
  ],
    thresholds: {
      'http_req_duration': ['p(90)<500'], // 95% das requests < 500ms
    },
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
