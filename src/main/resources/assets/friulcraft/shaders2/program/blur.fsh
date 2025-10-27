#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform vec2 BlurDir;
uniform float Radius;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 texel = 1.0 / InSize;
    vec4 color = vec4(0.0);
    float total = 0.0;

    for (float i = -Radius; i <= Radius; i++) {
        float weight = exp(-0.5 * (i / Radius) * (i / Radius));
        color += texture(DiffuseSampler, texCoord + BlurDir * texel * i) * weight;
        total += weight;
    }

    fragColor = color / total;
}
