import modal

app = modal.App("bitcoind-modal-hanpeng")

bitcoinImage = modal.Image.from_dockerfile("./Dockerfile")

@app.function(image=bitcoinImage)
def bitcoind():
    pass

@app.local_entrypoint()
def main():
    # bitcoind.local()
    pass