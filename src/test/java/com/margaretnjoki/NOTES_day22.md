# NOTES_day22.md

## Why `ResponseCode: "0"` does NOT mean the payment succeeded

When my application sends an STK Push request, Safaricom immediately returns a response with `ResponseCode: "0"`. This does **not** mean the customer has successfully paid. It only means that Safaricom has received my request and accepted it for processing.

At that point, the customer has not yet entered their M-Pesa PIN. They could still approve the payment, cancel it, enter the wrong PIN, or ignore the payment prompt completely. Therefore, I cannot assume the payment was successful just because I received `ResponseCode: "0"`.

The actual payment result is only known after Safaricom sends a callback to my backend.

---

## Normal Client–Server Flow

Before the callback, my backend is the **client** because it is the one initiating the request, while Safaricom is the **server** because it receives the request and responds.

```text
My Backend (Client)
        │
        │ 1. Request Access Token
        ▼
Safaricom API (Server)
        │
        │ Access Token
        ▲
        │
My Backend

        │
        │ 2. STK Push Request
        ▼
Safaricom API
        │
        │ ResponseCode = "0"
        ▲
        │
My Backend
```

At this stage, my backend is always the client because it is the one making the HTTP requests.

---

## Callback Reversal

After Safaricom sends the STK Push prompt to the customer's phone, it waits for the customer to enter their M-Pesa PIN, cancel the request, or let it time out.

Once the customer finishes interacting with the prompt, the roles reverse.

Safaricom now becomes the **client** because it initiates a new HTTP request to my backend. My backend becomes the **server** because it is now receiving the incoming request.

```text
Safaricom API (Client)
        │
        │ Callback Request
        ▼
My Backend (Server)
        │
        │ HTTP 200 OK
        ▲
        │
Safaricom API
```

This callback request contains the actual payment result, such as:

* Payment successful
* Payment cancelled
* Wrong PIN entered
* Request timed out

My backend processes the callback, updates the payment status if necessary, and responds with **HTTP 200 OK** to tell Safaricom that the callback was received successfully.

---

## Complete Flow

```text
Client (Postman / Frontend)
          │
          ▼
My Backend
          │
          │ Request Access Token
          ▼
Safaricom API
          │
          │ Access Token
          ▲
          │
My Backend
          │
          │ STK Push Request
          ▼
Safaricom API
          │
          │ ResponseCode = "0"
          ▲
          │
My Backend
          │
          ▼
Customer receives M-Pesa prompt
          │
          ▼
Customer enters PIN / Cancels / Times Out
          │
          ▼
Safaricom API
          │
          │ Callback Request
          ▼
My Backend
          │
          │ HTTP 200 OK
          ▲
          │
Safaricom API
```

